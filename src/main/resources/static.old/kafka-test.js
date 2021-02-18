function() {}
//const { Kafka } = require('kafkajs')
import Kafka from 'kafkajs';

const kafka = new Kafka({
  clientId: 'my-web-app',
  brokers: ['locahost:9092']
})

const consumer = kafka.consumer({ groupId: 'my-web-app' })

const runProducer = async () => {
  // Consuming
  await consumer.connect()
  await consumer.subscribe({ topic: 'dashboard-data', fromBeginning: true })

  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      console.log({
        partition,
        offset: message.offset,
        value: message.value.toString(),
      })
    },
  })
}

run().catch(console.error)