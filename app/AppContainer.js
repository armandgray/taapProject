import React, { Component } from 'react'
import { Drawer, Container } from 'native-base'
import { View, Navigator } from 'react-native'

import DrawerMenu from './components/DrawerMenu'
import FooterNav from './components/footer'

import SplashScene from './scenes/SplashScene'
import LoginScene from './scenes/LoginScene'
import HomeScene from './scenes/HomeScene'
import HistoryScene from './scenes/HistoryScene'

import theme from './theme/base-theme'

export default class AppContainer extends Component {
	constructor(props) {
		super(props)
		this.state = {
			toggled: false,
			store: this.props.store,
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
			case 'Home': {
				return <HomeScene {...route.passProps} navigator={navigator} />
			}
			case 'History': {
				return <HistoryScene {...route.passProps} navigator={navigator} />
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
			<Container style={{ flex: 1, flexDirection: 'column' }}>
				<Navigator style={{ flex: 11 }}
					ref = {(ref) => this._navigator = ref}
					configureScene = {this.configureScene.bind(this)}
					renderScene = {this.renderScene.bind(this)}
					initialRoute =  {{
						title: 'Home',
						passProps: {
							stores: this.state.store,
							theme: this.state.theme
						}
					}} />
				<FooterNav style={{ justifyContent: 'flex-end' }} navigator={this._navigator} />
			</Container>
		)
	}
}