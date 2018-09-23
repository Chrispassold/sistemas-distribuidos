const io = require('socket.io')

const server = io.listen(8000)

let clients = new Map()
let latestIdClient = 0
let coordinator = null
let isElecting = false

// event fired every time a new client connects:
server.on('connection', (socket) => {

    const id = ++latestIdClient

    // initialize this client's sequence number
    clients.set(socket, id)

    console.info(`Client connected [id=${id}]`)

    // when socket disconnects, remove it from the list:
    socket.on('disconnect', () => {
        clients.delete(socket)
        console.info(`Client gone [id=${id}]`)
    });
});

server.on('consume', (socket) => {
    console.info("Socket consume", socket)
    if (isElecting) return;

    if (coordinator === null) {
        electNewCoordinator();
    } else {
        if (socket !== coordinator) {
            coordinator.emit('consume', socket, clients.get(socket))
        }
    }
})


function electNewCoordinator() {
    if (coordinator !== null) return

    console.info("electing")

    isElecting = true

    let higherId = 0, higherClient = null
    for (const [client, id] of clients.entries()) {
        if (id > higherId) {
            higherId = id
            higherClient = client;
        }
    }

    if (higherClient !== null) {
        coordinator = higherClient
    }

    isElecting = false
}


// Kill coordinator
setInterval(() => {
    if (coordinator != null) {
        console.info(`Coordinator [id=${clients.get(coordinator)}] killed`)
        clients.delete(coordinator)
        coordinator.emit('kill')
        coordinator = null
    }
}, 60 * 1000)


console.info("Waiting clients...")