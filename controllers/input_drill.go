package controllers

import (
	"html/template"
	"net/http"
)

type Page struct {
	Alert string
}

func InputDrillController(w http.ResponseWriter, r *http.Request) {
	templates := template.Must(template.ParseFiles("views/input_drill.html"))
	var page Page

	if err := templates.ExecuteTemplate(w, "input_drill.html", page); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

}
