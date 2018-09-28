const io = require('socket.io')

const server = io.listen(8000)

let clients = new Map()
let latestIdClient = 0
let coordinator = null
let isElecting = false

// event fired every time a new client connects:
server.on('connection', (socket) => {

    const id = ++latestIdClient

    socket.emit("update",id)


    // initialize this client's sequence number
    clients.set(id, socket)

    console.info(`Client connected [id=${id}]`)

    // when socket disconnects, remove it from the list:
    socket.on('disconnect', () => {
        clients.delete(socket)
        console.info(`Client gone [id=${id}]`)
    });
});

console.info("Waiting clients...")