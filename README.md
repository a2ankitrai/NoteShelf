# Note_Shelf

A RESTful service acting as the middle-ware layer for [NoteShelf application](https://noteshelf.ankitrai.com/). This service expose endpoints for user registration, login and notes management and interact with the backend infrastructure to return the responses.

Tech stack:

- Spring Rest
- Spring Security
- Spring Actuators
- Aspect oriented programming for centralizing logging concerns
- Mapstruct for mapping entities
- Redis for caching user login sessions
- MongoDB and MySQL as the data store
- GCP Cloud Storage for images
 
