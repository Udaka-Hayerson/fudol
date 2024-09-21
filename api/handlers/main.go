package handlers

import "go.mongodb.org/mongo-driver/mongo"

type (
	Handler struct {
		Store Store
	}
	Store struct {
		DB    *mongo.Client
		Users *mongo.Collection
	}
)
