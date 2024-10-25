package models

type Todo struct {
	ID          int    `bson:"_id" json:"id"`
	Title       string `json:"title"`
	Description string `json:"description"`
	List        []Todo `json:"list"`
}
