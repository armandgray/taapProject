package main_test

import (
	"taap_project/routes"

	"net/http"
	"net/http/httptest"
	"testing"

	gmux "github.com/gorilla/mux"
)

var mux *gmux.Router
var req *http.Request
var err error
var respRec *httptest.ResponseRecorder

func setup() {
	mux = gmux.NewRouter()
	respRec = httptest.NewRecorder()
}

func TestGETNewDrillRoute200(t *testing.T) {
	setup()
	routes.AddDrillRoutes(mux)

	req, err = http.NewRequest("GET", "/taap/api/drills/new", nil)
	if err != nil {
		t.Fatal("Creating 'GET /taap/api/drills/new' request failed!")
	}

	mux.ServeHTTP(respRec, req)

	if respRec.Code != http.StatusOK {
		t.Fatal("Server error: Returned ", respRec.Code, " instead of ", http.StatusOK)
	}
}

func TestGETNewUserRoute200(t *testing.T) {
	setup()
	routes.AddUserRoutes(mux)

	req, err = http.NewRequest("GET", "/taap/api/users/new", nil)
	if err != nil {
		t.Fatal("Creating 'GET /taap/api/users/new' request failed!")
	}

	mux.ServeHTTP(respRec, req)

	if respRec.Code != http.StatusOK {
		t.Fatal("Server error: Returned ", respRec.Code, " instead of ", http.StatusOK)
	}
}
