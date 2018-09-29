const _exports = module.exports = {};

_exports.randomInRange = function (min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

_exports.randomProcess = function (processes) {
    if (!processes) return null

    const keys = processes.keys()

    return !!processes ? processes.get(keys[randomInRange(0, keys.length - 1)]) : null
}

_exports.electNewCoordinator = function (clients) {
    console.info("electing")

    if (!clients) return null;

    let higherId = 0
    for (const id in clients.keys()) {
        if (id > higherId) {
            higherId = id
        }
    }

    return higherId
}

_exports.log = function (message) {
    const date = new Date()
    console.info(`[${date.toLocaleString()}] - ${message}`)
}