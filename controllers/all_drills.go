package controllers

import (
	"net/http"
)

func AllDrillsController(w http.ResponseWriter, r *http.Request) {
	w.Write([]byte("All Drills"))
}
