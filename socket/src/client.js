const io = require('socket.io-client')
const ioClient = io.connect("http://localhost:8000")

let id = null

ioClient.on('update', (id) => {
    this.id = id
    console.log('id updated' + this.id)
})