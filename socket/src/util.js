const _exports = module.exports = {};

_exports.randomInRange = function (min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

_exports.electNewCoordinator = function (clients) {
    console.info("electing")

    let higherId = 0, higherClient = null
    for (const [client, id] of clients.entries()) {
        if (id > higherId) {
            higherId = id
            higherClient = client;
        }
    }

    if (higherClient !== null) {
        return higherClient
    }

    return null
}