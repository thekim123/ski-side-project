import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Routes, Route } from "react-router-dom"
import { TopBar } from './components/TopBar'
import { NavBar } from "./components/NavBar"
import { Layout } from './components/Layout';
import { Home } from "./pages/Home"
import { Login } from './features/auth/Login';
import { RequireAuth } from './features/auth/RequireAuth';
import { Board } from "./pages/Board"
import { Club } from "./pages/Club"
import { BoardWrite } from "./components/Board/BoardWrite"
import { EditBoard } from './components/Board/EditBoard';
import { loadResorts } from './action/resort';
import { ClubRegister } from './components/Club/ClubRegister';
import { BoardDetail } from './components/Board/BoardDetail';
import { ClubDetail } from './components/Club/ClubDetail';
import { ClubSecret } from './components/Club/ClubSecret';
import { Tayo } from './pages/Tayo';
import { TayoWrite } from './components/Tayo/TayoWrite';
import { TayoDetail } from './components/Tayo/TayoDetail';
import { CarPool } from './pages/CarPool';
import { CarPoolDetail } from './components/CarPool/CarPoolDetail';
import { CarPoolWrite } from './components/CarPool/CarPoolWrite';
import { EditCarPool } from './components/CarPool/EditCarPool';
import { ClubRoute } from './components/Club/ClubRoute';
import { ClubBoardWrite } from './components/Club/ClubBoardWrite';
import { EditClub } from './components/Club/EditClub';
import ClubBoardList from './components/Club/ClubBoardList';
import styled from 'styled-components'
import ClubBoardDetail from './components/Club/ClubBoardDetail';
import { ClubBoardEdit } from './components/Club/ClubBoardEdit';
import Mypage from './components/My/Mypage';
import KakaoLogin from './features/auth/KakaoLogin';
import MyPageDetail from './components/My/MyPageDetail';
import Chat from './components/Chat/Chat';
import ChatList from './pages/ChatList';
import CarPoolChat from './components/CarPool/CarPoolChat';
import Alarm from './components/Alarm/Alarm';
import MySubmit from './components/My/MySubmit';

function App() {
  const dispatch = useDispatch();
  //const cors = require('cors');
  //App.use(cors())

  return (
    <Wrapper>
      <TopBar />
      <Routes>
        <Route path="/" element={<Layout />}>
          {/* public routes */}
          <Route path="login" element={<Login />} />
          <Route path="kakaoLogin" element={<KakaoLogin />} />
          {/* 나중에 회원가입 창도 추가 */}

          {/* protected routes */}
          <Route element={<RequireAuth />}>
          <Route index element={<Home />} />
            <Route path="board" element={<Board />} />
            <Route path="board/write" element={<BoardWrite />} />
            <Route path="board/edit/:id" element={<EditBoard />} />
            <Route path="board/detail/:id" element={<BoardDetail />} />
            <Route path="club" element={<Club />} />
            <Route path="club/register" element={<ClubRegister />} />
            <Route path="club/route/:id" element={<ClubRoute />} />
            <Route path="club/detail/:id" element={<ClubDetail />} />
            <Route path="club/secret/:id" element={<ClubSecret />} />
            <Route path="club/edit/:id" element={<EditClub />} />
            <Route path="club/board/write/:isNotice" element={<ClubBoardWrite />} />
            <Route path="club/board/list/:id" element={<ClubBoardList />} />
            <Route path="club/board/detail/:id" element={<ClubBoardDetail />} />
            <Route path="club/board/edit/:id" element={<ClubBoardEdit />} />
            <Route path="tayo" element={<Tayo />} />
            <Route path="tayo/write" element={<TayoWrite />} />
            <Route path="tayo/detail/:id" element={<TayoDetail />} />
            <Route path="carpool" element={<CarPool />} />
            <Route path="carpool/write" element={<CarPoolWrite />} />
            <Route path="carpool/detail/:id" element={<CarPoolDetail />} />
            <Route path="carpool/edit/:id" element={<EditCarPool />} />
            <Route path="carpool/chat/:id/:room/:type" element={<CarPoolChat />} />
            <Route path="my" element={<Mypage />} />
            <Route path="my/detail" element={<MyPageDetail />} />
            <Route path="my/submit" element={<MySubmit />} />
            <Route path="chat" element={<ChatList />} />
            <Route path="chat/detail/:room/:type" element={<Chat />} />
            <Route path="alarm" element={<Alarm />} />
          </Route>
        </Route>
      </Routes>
      <NavBar />
    </Wrapper>
  );
}

export default App;

const Wrapper = styled.div`
button, input, textarea {
  font-family: nanum-square;
}
`