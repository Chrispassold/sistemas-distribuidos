const io = require('socket.io')
const utils = require('./util.js')
const server = io.listen(8000)

let clients = new Map()
let latestIdClient = 0
let coordinator = null
let isElecting = false
let _isBeingConsumed = false
const waiting = [] //push and shift
let tmoConsumingResource = null

// event fired every time a new client connects:
server.on('connection', (socket) => {

    const id = ++latestIdClient

    socket.emit("update", id)


    // initialize this client's sequence number
    clients.set(id, socket)

    utils.log(`Client connected [id=${id}]`)

    socket.on('consume', () => {
        processConsumeCoordinator(id)
    })

    // when socket disconnects, remove it from the list:
    socket.on('disconnect', () => {
        if (id === coordinator) {
            disconnectCoordinator()
            utils.log(`Coordinator has gone [id=${id}]`)
        } else {
            utils.log(`Client has gone [id=${id}]`)
        }

    });
});

function releaseAllWaitingRequests() {
    if (waiting.length > 0) {
        const id = waiting.shift()
        const obj = getProcessById(id)
        if (!!obj) {
            obj.emit('release')
        }
        releaseAllWaitingRequests()
    }
}

function disconnectCoordinator() {
    coordinator = null
    clients.delete(coordinator)
    utils.log('cleaning wait list')
    releaseAllWaitingRequests()
    _isBeingConsumed = false
}

function processConsumeCoordinator(id) {
    if (isElecting) {
        utils.log('Election occuring')
        return
    }

    if (coordinator === null) {
        isElecting = true
        coordinator = utils.electNewCoordinator(clients)
        if (!!coordinator) {
            utils.log("new coordinator id " + coordinator)
        } else {
            utils.log("coordinator not elected")
        }

        isElecting = false
    } else {
        if (id !== coordinator) {
            requestConsume(id)
        }
    }
}

//
function getProcessById(id) {
    return clients.get(id)
}

function requestConsume(id) {
    if (!id) return

    waiting.push(id)

    if (_isBeingConsumed) {
        utils.log(`Client ${id} is waiting...`)
    } else {
        consume(waiting.shift())
    }

}

function consume(id) {
    _isBeingConsumed = true
    utils.log(`Client ${id} is consuming`)
    tmoConsumingResource = setTimeout(() => releaseResource(id), utils.getTimeConsumingInMillis())
}

function releaseResource(id) {
    if (!!id) {
        const socket = getProcessById(id)
        if (!!socket) {
            socket.emit('release')
        }
    }

    if (waiting.length > 0) {
        consume(waiting.shift())
    } else {
        _isBeingConsumed = false
    }
}

// Kill coordinator
setInterval(() => {
    if (coordinator != null) {
        const socket = getProcessById(coordinator)
        if (!!socket) socket.disconnect()
    }
}, 60 * 1000)


console.info("Waiting clients...")