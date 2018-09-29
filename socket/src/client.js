const io = require('socket.io-client')
const utils = require('./util.js')
const ioClient = io.connect("http://localhost:8000")

let id = null
//timeouts
let tmoConsumeCoordinator = null
let isWaitingResponse = false

ioClient.on('update', (id) => {
    this.id = id
    utils.log('id updated ' + this.id)

    requestServerConsume()
})

ioClient.on('release', () => {
    releaseProcess()
})

function releaseProcess() {
    utils.log('releasing : ' + id)
    isWaitingResponse = false
}

function requestServerConsume() {
    utils.log('requestServerConsume : ' + isWaitingResponse)
    if (isWaitingResponse) {
        return
    }

    isWaitingResponse = true
    ioClient.emit('consume', id)

    tmoConsumeCoordinator = setTimeout(() => requestServerConsume(), (utils.randomInRange(10, 25) * 1000))
}