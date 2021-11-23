const errorRouter = require('./error');
const noteRouter = require('./note')

const configure = (app) => {
    app.use(noteRouter);
    app.use(errorRouter);
}

module.exports = configure;