'use strict';

module.exports.hello = async (event, context) => {
  return {
    statusCode: 200,
    body: JSON.stringify({ // Response body
      message: 'Hello World! Your function executed successfully!',
      input: event,
    }),
  };

};
