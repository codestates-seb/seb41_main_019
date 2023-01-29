import { useEffect, useState } from "react"
import Cookie from "../util/Cookie";
import axios from "axios";
import { EventSourcePolyfill } from 'event-source-polyfill';

export const useAlert = (alert) => {
    const [log, setLog] = useState([]);
    const [newLog, setNewLog] = useState(null);
    const [patchLog, setPatchLog] = useState(false);
    const cookie = new Cookie();
    const id = cookie.get("memberId");
    const Authorization = cookie.get("authorization");
    let EventSource = EventSourcePolyfill;

    useEffect(() => {
        axios({
            method: "get",
            url: `${process.env.REACT_APP_API}/notification/${id}`,
            headers: { Authorization }
        }).then(res => {
            setLog(res.data.data);
        }).catch(e => {
            console.log(e);
        })
    }, [id, Authorization, patchLog])

    const fatchLog = () => setPatchLog(!patchLog);

    const connect = () => {
        const url = `${process.env.REACT_APP_API}/notification/subscribe/${cookie.get("memberId")}`
        const sse = new EventSource(url, { headers: { "Authorization": cookie.get("authorization")} })

        sse.onopen = (res) => {
            console.log("sse connected...")
        }

        sse.addEventListener("sse", res => {
            try {
                const json = JSON.parse(res.data);
                setNewLog(json);
            }catch {
                console.log(1);
            }
            // console.log(JSON.parse(res.data));
        })

        sse.onerror = (err) => {
            console.log("sse closed...");
        }

        return sse;
    }

    const appendAlert = (data) => {
        console.log(data);
        const li = document.createElement("li");
        const img = document.createElement("img");
        img.src = data.profileImage;
        img.alt = "img";

        const span = document.createElement("span");
        span.textContent = `${data.userName}님이 ${soltText(data.sseType)}`;

        li.append(span);
        li.append(img);

        console.log(alert)
        alert.current.append(li);
    }

    const soltText = (type) => {
        switch(type) {
            case "postLike" : return "님이 회원님의 게시글을 좋아합니다."
            default: return;
        }
    }

    return {log, newLog, setLog, fatchLog, connect, soltText};
}