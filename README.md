# Gardener's Diary

Gardener's Diary is an application that was built as part of the final year project for my degree. The application aims to help keep gardeners organised when managing their gardens, allowing a gardener to keep track of what plants are planted in their garden and when a certain task (e.g. watering) needs to be done for a certain plant.

The application was written using the [Spark](http://sparkjava.com/) micro framework (to implement the server side functionality), [ActiveJDBC](http://javalite.io/activejdbc) (to help abstract database functionality), [KonvaJS](https://konvajs.github.io/) (to implement the garden image drawing) and [JQuery](https://jquery.com/) (to help with some client sided functionality)


## Features

 - Ability to upload an image into to application to represent a garden
 - Ability to draw "plant regions" on the uploaded image to represent where a certain plant is planted
 - Ability to assign tasks that need to be done for a certain plant
 - Ability to add reminders for tasks (implemented using the [MailGun](https://www.mailgun.com/) API)
 - Ability to search for gardening information (implemented using the [OpenFarm](https://openfarm.cc/) API)

## Possible improvements
 - Follow proper RESTful conventions in naming of routes
 	- Some routes do not follow proper REST naming conventions, this might cause a difficulity when developing a third party client (e.g. a mobile client) and the developer expecting standard naming to exist.
 - Add integration tests
 - Security
 	- As the application is designed to be self-hosted and no real sensitive data was being handled by the application no security features were taken into account when developing the application. This is a pretty big flaw and should be addressed.
 - Multi garden support
 	- Currently the application only supports one garden, schema support exists for multiple gardens but this needs to be worked into the application.
 - Multi user support
 	- Currently the application only supports a single user (as per the client's requirements), improvements could be done to support multiple users.
 - Proper seperation of view related code
 	- Currently the view side of the application contains a lot of repetation and could be said to be moderately tightly coupled. Improvements of this side of the application were undertaken in the form of splitting the view into templates but due to time constraints further development was not done in this manner.
