package controllers_test

import (
	"net/http"
	"testing"

	gmux "github.com/gorilla/mux"
)

var mux *gmux.Router
var req *http.Request
var err error

func setup() {
	mux = gmux.NewRouter()
}

func TestNewDrillController(t *testing.T) {
	req, err = http.NewRequest("GET", "/", nil)
	if err != nil {
		t.Fatal("Creating 'GET /' request failed!")
	}
}
