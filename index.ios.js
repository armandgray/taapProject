import React, { Component } from 'react';
import { AppRegistry } from 'react-native';
import AppContainer from './app/AppContainer'
import { Navigator } from 'react-native'

import SettingsStore from './app/stores/SettingsStore'
import AuthStore from './app/stores/AuthStore'

import SplashScene from './app/scenes/SplashScene'
import LoginScene from './app/scenes/LoginScene'
import HomeScene from './app/scenes/HomeScene'

import theme from './app/theme/base-theme'

const settings = new SettingsStore()
const authStore = new AuthStore()

export default class taapProject extends Component {
	constructor() {
		super()
		this.state = {
			toggled: false,
			store: {
				settings: settings,
				auth: authStore
			},
			theme: theme
		}
	}

	renderScene(route, navigator) {
		switch(route.title) {
			case 'Splash': {
				return <SplashScene {...route.passProps} navigator={navigator} />
			}
			case 'Login': {
				return <LoginScene {...route.passProps} navigator={navigator} />
			}
			case 'App': {
				return <AppContainer />
			}
			default: {
				return null
			}
		}
	}
	configureScene(route, routeStack) {
		return Navigator.SceneConfigs.PushFromRight
	}
  	render() {
	    return (
	    	<Navigator 
				ref = {(ref) => this._navigator = ref}
				configureScene = {this.configureScene.bind(this)}
				renderScene = {this.renderScene.bind(this)}
				initialRoute =  {{
					title: 'Splash',
					passProps: {
						stores: this.state.store,
						theme: this.state.theme
					}
				}} />
	    );
	}
}

AppRegistry.registerComponent('taapProject', () => taapProject);
