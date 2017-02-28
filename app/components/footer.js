import React, { Component } from 'react'
import { Container, Content, Footer, FooterTab, Button, Text } from 'native-base'
import { Image } from 'react-native'

export default class FooterNav extends Component {
	constructor(props) {
		super(props)
	}
	postScene() {
		this.props.navigator.push({
			title: 'History',
			passProps: this.props
		})
	}
	render() {
		return (
			<Container>
				<Footer>
					<FooterTab>
						<Button onPress={this.postScene.bind(this)}>
							<Image style={styleImage}
					        	source={require('../../images/ic_home_white_48dp.png')} />
							<Text style={styleText}>Home</Text>
						</Button>
						<Button>
							<Image style={styleImage}
					        	source={require('../../images/ic_clipboard_account_white_48dp.png')} />
							<Text style={styleText}>Training</Text>
						</Button>
						<Button>
							<Image style={styleImage}
					        	source={require('../../images/ic_dribbble_white_48dp.png')} />
							<Text style={styleText}>Drills</Text>
						</Button>
						<Button>
							<Image style={styleImage}
					        	source={require('../../images/calendar_variantxxxhdpi.png')} />
							<Text style={styleText}>Logs</Text>
						</Button>
						<Button>
							<Image style={styleImage}
					        	source={require('../../images/ic_account_outline_white_48dp.png')} />
							<Text style={styleText}>Profile</Text>
						</Button>
					</FooterTab>
				</Footer>
			</Container>
		);
	}
}

const styleText = {
	color: '#333333',
	fontSize: 8
}

const styleImage = {
	width: 18, 
	height: 18, 
	tintColor: '#949494'
}