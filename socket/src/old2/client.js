const io = require('socket.io-client')
const util = require('../util.js')

const ioClient = io.connect("http://localhost:8000")
const waiting = [] //push and shift
let isRequestPending = false
let isBeingConsumed = false
let _id = null

//timeouts
let tmoConsumeCoordinator = null
let tmoConsumingResource = null

ioClient.on("update id", (id) => _id = id)

ioClient.on('disconnect', () => {
    clearTimeout(tmoConsumeCoordinator)
    clearTimeout(tmoConsumingResource)

    while (waiting.length > 0) {
        const data = waiting.shift()
        ioClient.to(data.socketId).emit('flush')
    }
})


// ================ Client ================

function consumeCoordinator() {
    if (isRequestPending) return

    console.info("Send request to consume coordinator")

    isRequestPending = true
    ioClient.emit('consume resource', ioClient.id)

    tmoConsumeCoordinator = setTimeout(() => consumeCoordinator(), (util.randomInRange(10, 25) * 1000))
}

consumeCoordinator()


// ================ Coordinator ================


ioClient.on('consume', (socketId, customId) => {
    console.log('consuming coordinator')
    consumeResource({socketId, customId})
});


function consumeResource(data) {
    if (!data) return;

    waiting.push(data)

    if (isBeingConsumed) {
        console.info(`Client ${data.customId} is waiting...`)
    } else {
        const dataShift = waiting.shift()
        consume(dataShift)
    }
}

function consume(data) {
    isBeingConsumed = true
    console.info(`Client ${data.customId} is consuming`)
    tmoConsumingResource = setTimeout(() => releaseResource(data.socketId), util.randomInRange(5, 15))
}

function releaseResource(socketId) {
    ioClient.to(socketId).emit('flush')
    if (waiting.length > 0) {
        consume(waiting.shift())
    } else {
        isBeingConsumed = false
    }
}