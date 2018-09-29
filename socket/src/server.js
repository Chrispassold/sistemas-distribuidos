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
        clients.delete(id)

        if (id === coordinator) {
            coordinator = null
            clearTimeout(tmoConsumingResource)
            waiting.forEach(id => {
                const obj = getProcessById(id)
                if (!!obj) {
                    obj.emit('release')
                }
            })
        }

        utils.log(`Client gone [id=${id}]`)
    });
});

function processConsumeCoordinator(id) {
    if (isElecting) {
        utils.log('Election occuring')
        return
    }

    if (!coordinator) {
        isElecting = true
        coordinator = utils.electNewCoordinator(clients)
        if (!!coordinator) {
            utils.log("new coordinator id " + coordinator)
        } else {
            utils.log("coordinator not elected")
        }

        isElecting = false
    } else {
        if (!!id && id !== coordinator) {
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

    console.log(_isBeingConsumed)
    if (_isBeingConsumed) {
        utils.log(`Client ${id} is waiting...`)
    } else {
        consume(waiting.shift())
    }

}

function consume(id) {
    _isBeingConsumed = true
    utils.log(`Client ${id} is consuming`)
    tmoConsumingResource = setTimeout(() => releaseResource(id), utils.randomInRange(5, 15) * 1000)
}

function releaseResource(id) {
    const socket = getProcessById(id)
    socket.emit('release')
    if (waiting.length > 0) {
        consume(waiting.shift())
    } else {
        _isBeingConsumed = false
    }
}


console.info("Waiting clients...")