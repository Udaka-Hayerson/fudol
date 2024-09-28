package middlewares

import (
	"fudol_api/constants"
	"fudol_api/helpers"
	"os"

	"github.com/golang-jwt/jwt/v5"
	echojwt "github.com/labstack/echo-jwt/v4"
	"github.com/labstack/echo/v4"
)

func AuthMiddleware() echo.MiddlewareFunc {
	return echojwt.WithConfig(echojwt.Config{
		SigningKey: []byte(os.Getenv("TOKEN_KEY")),
		ContextKey: constants.TokenData,
		NewClaimsFunc: func(c echo.Context) jwt.Claims {
			return new(helpers.JwtCustomClaims)
		},
	})
}
