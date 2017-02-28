import React, { Component } from 'react'
import { Container, Content, Footer, FooterTab, Button, Text } from 'native-base'
import { Image } from 'react-native'

export default class FooterNav extends Component {
	render() {
		return (
			<Container>
				<Footer>
					<FooterTab>
						<Button>
							<Image style={{ width: 18, height: 18, tintColor: '#949494' }}
					        	source={require('../../images/ic_home_white_48dp.png')} />
							<Text style={styleText}>Home</Text>
						</Button>
						<Button>
							<Image style={{ width: 20, height: 20, tintColor: '#949494' }}
					        	source={require('../../images/ic_clipboard_account_white_48dp.png')} />
							<Text style={styleText}>Training</Text>
						</Button>
						<Button active>
							<Image style={{ width: 20, height: 20, tintColor: '#949494' }}
					        	source={require('../../images/ic_dribbble_white_48dp.png')} />
							<Text style={styleText}>Drills</Text>
						</Button>
						<Button>
							<Image style={{ width: 15, height: 15, tintColor: '#949494' }}
					        	source={require('../../images/calendar_variantxxxhdpi.png')} />
							<Text style={styleText}>Logs</Text>
						</Button>
						<Button>
							<Image style={{ width: 20, height: 20, tintColor: '#949494' }}
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
	color: '#949494',
	fontSize: 10
}