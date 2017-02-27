import firebase from 'firebase'

const config = {
    apiKey: "AIzaSyB4EpCX83r-cHw-puWy1RgrZk-wN-frJsQ",
    authDomain: "taapproject-5ca56.firebaseapp.com",
    databaseURL: "https://taapproject-5ca56.firebaseio.com",
    storageBucket: "taapproject-5ca56.appspot.com",
    messagingSenderId: "603145959618"
}

export default class SettingsStore {
	constructor() {
		firebase.initializeApp(config)

		this.splashTime = 1500
		this.splashImg = require('../../images/splash.jpg')
		this.loginBG = require('../../images/login.jpg')
	}

	get LoginBG() { return this.loginBG }
	get SplashTime() { return this.splashTime }
	get SplashImg() { return this.splashImg }
}