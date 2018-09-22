const io = require('socket.io-client')
const util = require('./util.js')

const ioClient = io.connect("http://localhost:8000")

// ================ Client ================

let isRequestPending = false

function consumeCoordinator() {
    if (isRequestPending) return

    console.info("Send request to consume coordinator")

    isRequestPending = true
    ioClient.emit('consume')

    setTimeout(() => consumeCoordinator(), (util.randomInRange(10, 25) * 1000))
}

consumeCoordinator()


// ================ Coordinator ================

let waiting = [] //push and shift

ioClient.on('consume', (socket, id) => {
    consumeResource(socket, id)
});

ioClient.on('kill', () => {
    waiting.forEach(socket => socket.emit('flush'))
    ioClient.disconnect()
});

ioClient.on('flush', () => isRequestPending = false)

function consumeResource(client, id) {

    if (!client) return;

    if (waiting.length > 0) {
        waiting.push(client)
        console.info(`Client ${id} is waiting...`)
    } else {
        console.info(`Client ${id} is consuming`)
        setTimeout(() => releaseResource(client), util.randomInRange(5, 15))
    }
}

function releaseResource(client) {
    client.emit('flush')
    consumeResource(waiting.shift())
}