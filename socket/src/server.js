const io = require('socket.io')

const server = io.listen(8000)


const _clients = new Map()
let _latestIdClient = 0


// event fired every time a new client connects:
server.on('connection', (socket) => {
    const customId = ++_latestIdClient

    socket.emit('update id', customId)

    console.info(`Client connected [id=${customId}]`)
    // when socket disconnects, remove it from the list:
    socket.on('disconnect', () => {
        console.info(`Client gone [id=${_clients.get(socket.id)}]`)
        _clients.delete(socket.id)
    });

    socket.on('consume', (socketId) => console.log("socket id => " + socketId))

    _clients.set(socket, customId)
})