package controllers_test

import (
	"net/http"
	"net/http/httptest"
	"testing"

	gmux "github.com/gorilla/mux"
)

func TestNewDrillController(t *testing.T) {
	mux := gmux.NewRouter()
	respRec := httptest.NewRecorder()
	req, err := http.NewRequest("GET", "/", nil)
	if err != nil {
		t.Fatal("Creating 'GET /' request failed!")
	}

	mux.ServeHTTP(respRec, req)
	if respRec.Code != http.StatusOK {
		t.Fatal("Server error: Returned ", respRec.Code, " instead of ", http.StatusOK)
	}
}
