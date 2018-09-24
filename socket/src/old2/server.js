const io = require('socket.io')
const util = require('../util.js')

const server = io.listen(8000)

const _clients = new Map()
let _latestIdClient = 0
let _coordinator = null
let _isElecting = false


// event fired every time a new client connects:
server.on('connection', (socket) => {
    const customId = ++_latestIdClient

    // initialize this client's sequence number
    _clients.set(socket.id, customId)
    socket.emit('update id', customId)

    console.info(`Client connected [id=${customId}]`)
    // when socket disconnects, remove it from the list:
    socket.on('disconnect', () => {
        console.info(`Client gone [id=${_clients.get(socket.id)}]`)
        _clients.delete(socket.id)
    });
});

server.on('consume resource', (socketId) => {
    console.info("Socket consume", socketId)
    if (_isElecting) return;

    if (_coordinator === null) {
        if (!_isElecting) {
            _isElecting = true
            updateCoordinator(util.electNewCoordinator(_clients))
            _isElecting = false
        }
    } else {
        if (socketId !== _coordinator.id) {
            _coordinator.emit('consume', socketId, _clients.get(socketId))
        }
    }
})

function updateCoordinator(coordinator) {
    if (coordinator === null) return

    _coordinator = coordinator
    console.info("New coordinator : " + _clients.get(coordinator.id))
}

// Kill coordinator
setInterval(() => {
    if (_coordinator != null) {
        console.info(`Coordinator [id=${_clients.get(_coordinator.id)}] killed`)
        _coordinator.disconnect()
        _clients.delete(_coordinator.id)
        _coordinator = null
    }
}, 60 * 1000)

console.info("Waiting _clients...")