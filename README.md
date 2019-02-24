# OpenC2-Worker

Also see: [OpenC2-Orchestrator](https://github.com/StephenOTT/OpenC2-Orchestrator)

## OpenC2 Orchestrator with Worker
![design](./docs/design/openc2-design.png)

## OpenC2 Orchestrator with Worker using a proxy network to limit internal network exposure
![design](./docs/design/openc2-design-proxy.png)

## OpenC2 Orchestrator/Worker Message Flow
![design](./docs/design/openc2-design-message-flow.png)

## OpenC2 Orchestrator/Worker Multiple Configuration Options
![design](./docs/design/openc2-design-worker-config.png)

# Deploy

`java -jar openc2-worker-v0.1.jar`


# Notes

Launcher Config:

VM Options: `-Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory`

Arguments: `run io.digitalstate.openc2.worker.MainVerticle`