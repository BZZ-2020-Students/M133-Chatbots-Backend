# M133-Chatbots

## Needed things

Add 'config.properties' to the root of resources folder or add the properties to the environment variables. Environment
variables are higher priority than config.properties and will override the values in config.properties.

### Config properties

#### Optional properties

- jwt.issuer: Issuer of the JWT token
    - Default: zwazels-chatbot
- jwt.secret: Secret of the JWT token
    - Default: this-secret-is-a-very-secure-secret
- jwt.name: Name of the JWT token
    - Default: zwazel-jwt-chatbot
- cors.allowed.origins: Allowed origins for CORS
    - Default: http://localhost:3000

#### Required properties