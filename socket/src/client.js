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

// when socket disconnects, remove it from the list:
ioClient.on('disconnect', () => {
    utils.log('Disconected')
    clearTimeout(tmoConsumeCoordinator)
});

ioClient.on('release', () => {
    releaseProcess()
})

function releaseProcess() {
    utils.log('released')
    isWaitingResponse = false
}

function requestServerConsume() {
    if (!isWaitingResponse) {
        isWaitingResponse = true
        ioClient.emit('consume', id)
    }else{
        utils.log('is waiting response')
    }

    tmoConsumeCoordinator = setTimeout(() => requestServerConsume(), utils.getTimeRequestConsumingInMillis())
}