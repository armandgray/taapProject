import React, { Component } from 'react'
import { Drawer } from 'native-base'
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

	toggleDrawer() {
		this.state.toggled ? this._drawer.close() : this._drawer.open()
	}
	openDrawer() {
		this.setState({toggled: true})
	}
	closeDrawer() {
		this.setState({toggled: false})
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
			<Drawer
				ref = {(ref) => this._drawer = ref}
				type = 'displace'
				content = { <DrawerMenu navigator={this._navigator} theme={this.state.theme} /> }
				onClose = {this.closeDrawer.bind(this)}
				onOpen = {this.openDrawer.bind(this)}
				openDrawerOffset = {0.2}
		        closedDrawerOffset={0}
		        panOpenMask={0.2}
				>

				<Navigator 
					ref = {(ref) => this._navigator = ref}
					configureScene = {this.configureScene.bind(this)}
					renderScene = {this.renderScene.bind(this)}
					initialRoute =  {{
						title: 'Home',
						passProps: {
							stores: this.state.store,
							toggleDrawer: this.toggleDrawer.bind(this),
							theme: this.state.theme
						}
					}} />
				<FooterNav />
			</Drawer>
		)
	}
}