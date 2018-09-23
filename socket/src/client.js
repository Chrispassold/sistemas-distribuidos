const io = require('socket.io-client')
const util = require('./util.js')

const ioClient = io.connect("http://localhost:8000")

let isRequestPending = false
let _id = null
const waiting = [] //push and shift

ioClient.on("update id", (id) => _id = id)

ioClient.on('consume', (client) => {
    console.log('consuming coordinator')
    consumeResource(client)
})

ioClient.on('kill', () => {
    waiting.forEach(socket => socket.emit('flush'))
    ioClient.disconnect()
})

ioClient.on('flush', () => isRequestPending = false)

function consumeCoordinator() {
    if (isRequestPending) return

    console.info("Send request to consume coordinator")

    isRequestPending = true
    ioClient.emit('consume', _id)

    setTimeout(() => consumeCoordinator(), (util.randomInRange(10, 25) * 1000))
}


function consumeResource(client) {

    if (!client) return;

    if (waiting.length > 0) {
        waiting.push(client)
        console.info(`Client is waiting...`)
    } else {
        console.info(`Client is consuming`)
        setTimeout(() => releaseResource(client), (util.randomInRange(5, 15) * 1000))
    }
}

function releaseResource(client) {
    client.emit('flush')
    consumeResource(waiting.shift())
}

consumeCoordinator()