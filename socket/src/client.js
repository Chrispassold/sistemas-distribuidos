const io = require('socket.io-client')

const ioClient = io.connect("http://localhost:8000")
let _id = null

ioClient.on("update id", (id) => {
    _id = id
    console.info('update id')
})

ioClient.emit('consume', ioClient.id)