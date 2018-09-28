const io = require('socket.io')
const commands = require('./command.js')
const utils = require('../util.js')

const server = io.listen(8000)

const _sockets = new Map() //socket id -> {socket, id}
let _coordinator = null
let _latestIdClient = 0

// event fired every time a new client connects:
server.on('connection', (socket) => {
    const id = ++_latestIdClient

    console.info(`Client connected [id=${id}]`)
    _sockets.set(socket.id, {socket, id})

    socket.emit('updateId', id, socket.id)

    socket.on('releaseProcess', () => {
        socket.emit('command', commands.RELEASE)
    })

    socket.on('requestConsume', (socketId) => processConsumeCoordinator(_sockets.get(socketId)))

    // when socket disconnects, remove it from the list:
    socket.on('disconnect', () => {
        console.info(`Client gone [id=${_sockets.get(socket.id).id}]`)
        _sockets.delete(socket.id)
        if (socket === _coordinator) {
            _coordinator = null
        }
    })
})

function processConsumeCoordinator(process) {
    if (!_coordinator) {
        _coordinator = utils.electNewCoordinator(_sockets)
        if (!!_coordinator)
            console.info("new coordinator id " + _sockets.get(_coordinator.id).id)
    } else {
        if (!!process && process.socket !== _coordinator) {
            _coordinator.emit("command", commands.CONSUME,
                {id: process.id, socketId: process.socket.id})
        }
    }
}

// Kill coordinator
setInterval(() => {
    if (!!_coordinator) {
        console.info(`Coordinator [id=${_sockets.get(_coordinator.id).id}] killed`)
        _sockets.delete(_coordinator.id)
        _coordinator.emit('kill')
        _coordinator = null
    }
}, 60 * 1000)


console.info("Waiting clients...")
