import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { getUser, kakaoLogin } from '../../action/auth';
import Send from "../../components/common/Send";

export default function KakaoLogin() {
    const PARAMS = new URL(document.location).searchParams;
    const KAKAO_CODE = PARAMS.get('code');
    const ERROR = PARAMS.get('error');
    const isAuth = useSelector(state => state.auth.isAuth);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const REDIRECT_URI='http://15.165.81.194:3000/kakaoLogin'
    const REST_API_KEY='30d8d88d8914487594ffefdce38681cc'

    const getKakaoToken = () => {
        fetch(`https://kauth.kakao.com/oauth/token`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            //body: `grant_type=authorization_code&client_id=${process.env.REACT_APP_REST_API_KEY}&redirect_uri=${process.env.REACT_APP_REDIRECT_URI}&code=${KAKAO_CODE}`,
            body: `grant_type=authorization_code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&code=${KAKAO_CODE}`,

        }).then(res => res.json())
        .then(data => {
            if (data.access_token) {
                fetch(`https://kapi.kakao.com/v2/user/me`, {
                    method: 'POST',
                    headers: { 
                        'Authorization': `Bearer ${data.access_token}`,
                        'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
                    }
                }).then(res => res.json())
                .then(
                    data => Send({
                    url: 'http://15.165.81.194:8080/oauth/jwt/kakao',
                    method: 'POST',
                    data: data,
                })
                ).then(resp => {
                    dispatch(kakaoLogin(resp));
                    navigate('/')
                }).catch(error => console.log(error));
            } else {
                console.log("fail");
                navigate('/login')
            }
        })
    }

    useEffect(() => {
        if (ERROR === null) {
        getKakaoToken();
        } else if (ERROR === 'consent_required') {
            console.log("회원가입 필요")
        } else {
            navigate('/login');
        }
    }, [])

    
    return (
    <div>
        {/* loading page? */}
    </div>
    )
}
