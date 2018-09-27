const io = require('socket.io-client')
const commands = require('./command.js')
const utils = require('./util.js')
const ioClient = io.connect("http://localhost:8000")

let _isBeingConsumed = false
const waiting = [] //push and shift
let _isWaitingResponse = false
//timeouts
let tmoConsumeCoordinator = null
let tmoConsumingResource = null

let _id = null
let _socketId = null

ioClient.on('updateId', (id, socketId) => {
    _socketId = socketId
    _id = id
    console.log('ID updated => ' + _id)
})

ioClient.on('kill', () => {
    waiting.forEach(process => ioClient.emit('releaseProcess', process.socketId))
    ioClient.disconnect()
});

ioClient.on('command', (command, who) => {
    switch (command) {
        //only coordinator
        case commands.CONSUME : {
            requestConsume(who)
            break
        }

        //all
        case commands.RELEASE: {
            releaseProcess()
            break
        }
        default:
            console.log('command not found => ' + command)
    }
})

//client
function releaseProcess() {
    console.log("releasing : " + _id)
    _isWaitingResponse = false
}

function requestServerConsume() {
    if (_isWaitingResponse) return

    _isWaitingResponse = true
    ioClient.emit('requestConsume', _socketId)

    tmoConsumeCoordinator = setTimeout(() => requestServerConsume(), (utils.randomInRange(10, 25) * 1000))
}

//coordinator
function requestConsume(who) {
    if (!who) return

    waiting.push(who)

    console.log(_isBeingConsumed)
    if (_isBeingConsumed) {
        console.info(`Client ${who.id} is waiting...`)
    } else {
        consume(waiting.shift())
    }

}

function consume(who) {
    _isBeingConsumed = true
    console.info(`Client ${who.id} is consuming`)
    tmoConsumingResource = setTimeout(() => releaseResource(who.socketId), utils.randomInRange(5, 15))
}

function releaseResource(socketId) {
    ioClient.emit('releaseProcess', socketId)
    if (waiting.length > 0) {
        consume(waiting.shift())
    } else {
        _isBeingConsumed = false
    }
}


//end

requestServerConsume()