import firebase from 'firebase'
import MobxFirebaseStore from 'mobx-firebase-store'

const config = {
    apiKey: "AIzaSyB4EpCX83r-cHw-puWy1RgrZk-wN-frJsQ",
    authDomain: "taapproject-5ca56.firebaseapp.com",
    databaseURL: "https://taapproject-5ca56.firebaseio.com",
    storageBucket: "taapproject-5ca56.appspot.com",
    messagingSenderId: "603145959618"
}

export default class SettingsStore extends MobxFirebaseStore {
	constructor() {
		firebase.initializeApp(config)
		super(firebase.database().ref())

		this.splashTime = 5000
		this.splashImg = require('../../images/splash.jpg')
	}

	get SplashTime() { return this.splashTime }
	get SplashImg() { return this.splashImg }
}