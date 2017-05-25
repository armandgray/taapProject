package controllers

import (
	"net/http"
)

func InputDrillController(w http.ResponseWriter, r *http.Request) {
	w.Write([]byte("input"))
}
