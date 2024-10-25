package handlers

import (
	"fudol_api/constants"
	"fudol_api/helpers"
	"os"
	"time"

	"github.com/golang-jwt/jwt/v5"
	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

type (
	Handler struct {
		Store Store
	}

	Store struct {
		DB        *mongo.Client
		Users     *mongo.Collection
		Fudols    *mongo.Collection
		TodoLists *mongo.Collection
	}
)

func (h *Handler) Gentoken(id primitive.ObjectID, role helpers.Role) (token string, err error) {
	token, err = jwt.NewWithClaims(
		jwt.SigningMethodHS256,
		helpers.JwtCustomClaims{
			Role:    role,
			User_id: id,
			RegisteredClaims: jwt.RegisteredClaims{
				ExpiresAt: jwt.NewNumericDate(time.Now().Add(time.Hour * 72)),
			},
		},
	).SignedString([]byte(os.Getenv("TOKEN_KEY")))

	return
}

func (h *Handler) GetTokenClaims(c echo.Context) (claims *helpers.JwtCustomClaims) {
	token, _ := c.Get(constants.TokenData).(*jwt.Token)
	claims, _ = token.Claims.(*helpers.JwtCustomClaims)

	return
}
