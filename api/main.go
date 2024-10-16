package main

import (
	"context"
	"fmt"
	_ "fudol_api/docs"
	"fudol_api/handlers"
	"fudol_api/helpers"
	"fudol_api/middlewares"
	"net/http"
	"os"

	"github.com/go-playground/validator/v10"
	"github.com/joho/godotenv"
	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	"github.com/labstack/gommon/log"
	echoSwagger "github.com/swaggo/echo-swagger"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

// @title						Fudol OpenAPI doc
// @version					0.0.1
// @description				Work hard, play hard
// @termsOfService				http://swagger.io/terms/
//
// @contact.name				Backend developer
// @contact.url				https://t.me/bringmetheaugust
// @contact.email				nahuy@kaniv.com
//
// @license.name				Apache 2.0
// @license.url				http://www.apache.org/licenses/LICENSE-2.0.html
//
// @host						http://localhost:8080
// @BasePath					/
//
// @securityDefinitions.apikey	Bearer
// @in							header
// @name						token
func main() {
	e := echo.New()
	e.Logger.SetLevel(log.ERROR)
	e.Use(middleware.CORS())
	e.Validator = &helpers.CustomValidator{Validator: validator.New()}

	if os.Getenv("IS_CONTAINER") == "" {
		if err := godotenv.Load("../.env"); err != nil {
			panic("Error loading .env file")
		}
	}

	credential := options.Credential{
		Username: os.Getenv("DB_USER"),
		Password: os.Getenv("DB_PASSWORD"),
	}
	clientOptions := options.Client().SetHosts([]string{os.Getenv("DB_HOST")}).SetAuth(credential)
	client, err := mongo.Connect(context.TODO(), clientOptions)

	if err != nil {
		panic(fmt.Sprintf("Err during connection to DB: %v", err))
	}

	defer func() {
		if err = client.Disconnect(context.TODO()); err != nil {
			panic(err)
		}
	}()

	h := handlers.Handler{
		Store: handlers.Store{
			DB:    client,
			Users: client.Database("client").Collection("users"),
		},
	}

	h.Store.Users.Indexes().CreateOne(
		context.TODO(),
		mongo.IndexModel{
			Keys:    bson.D{{Key: "login", Value: -1}, {Key: "nickname", Value: -1}},
			Options: options.Index().SetUnique(true),
		},
	)

	e.GET("/docs/*", echoSwagger.WrapHandler)
	e.POST("/signup", h.SignUp)
	e.POST("/signin", h.SignIn)
	e.GET("/users", h.GetUserList)
	e.DELETE("/users", h.RemoveUsers)
	e.GET("/user", h.GetUserData, middlewares.AuthMiddleware())
	e.PATCH("/timecount/increase", h.TimeCountIncrease, middlewares.AuthMiddleware())
	e.GET("/", func(c echo.Context) error {
		return c.String(http.StatusOK, "Welcome to Fudol API")
	})

	e.Logger.Fatal(e.Start(":8080"))
}
