import styled from "styled-components";
import Uploader from "./Uploader";
import Tags from "./Tags";
import CloseBtn from "../CloseBtn";
import { useRef, useState, useEffect } from "react";
import { BlueBtn } from "../BlueBtn";
import axios from "axios";
import Cookie from "../../../util/Cookie";

const Wrapper= styled.div`
    background-color: white;
    position: fixed;
    top:50%;
    left:50%;
    transform:translate(-50%, -50%);
    display: flex;
    flex-direction: column;
    gap: 30px;
    width: 800px;
    height: auto;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    margin: 0 auto;
    padding: 50px;
    color: gray;
    z-index: 1000;
    box-shadow: 4px 4px 4px 1px rgba(0,0,0,0.3); 

    > div:first-child {
        margin: 0 0 0 auto;
    }

    > div:nth-of-type(2) {
        display: flex;
    }

    ul {
        display: flex;
        list-style: none;

        img {
        display: flex;
        width: 150px; 
        @media screen and (max-width: 1255px) {
            width: 150px;
            }    
        }     
    }

    button:last-of-type{
        background-color: #D96848;
    }

    @media screen and (max-width: 1255px) {
        width: 700px;
    }

    @media screen and (max-width: 770px) {
        width: 480px;
    }
`;

const StyledTextarea = styled.textarea`
    width: 100%;
    height: 200px;
    line-height: 1;
    resize: none;
    padding: 10px 10px;
    outline: none;
    border: 1px solid #dbdbdb;
    border-radius: 5px;

    :focus {
        box-shadow: 0 0 6px #5e8b7e;
    }
`;

// 기능 추가: 사진 x클릭시 사진 삭제, 태그 줄 자동바꿈,
const EditPost = ({ handleEdit, curPost }) => {
    const [images, setImages] = useState([]);
    const [files, setFiles] = useState([]);
    const [value, setValue] = useState("");
    const [tags, setTags] = useState([]);
    const fileInputRef = useRef([]);
    const fileInputs = useRef(null);
    const cookie = new Cookie();

    const handleImg = (e) => {
        const file = e.target.files[0];
        setFiles([...files, file]);
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = () => {
            setImages([...images, reader.result]);
        }
    };

    const deleteImg = (e) => {
        const delIdx = e.target.id;
        const newFiles = files.slice();
        const newImgs = images.slice();
        newFiles.splice(delIdx, 1)
        newImgs.splice(delIdx, 1)
        setFiles(newFiles);
        setImages(newImgs);
        fileInputs.current.childNodes[delIdx].remove();
    };

    useEffect(() => {
        setValue(curPost.postingContent);
        setTags(curPost.tags.map(tag => tag.tagName))
    },[])

    const handleValue = (e) => {
        setValue(e.target.value);
    };

    const addTags = (e) => {
        if(e.key === "Enter" && e.target.value.length > 0 && tags.length < 5) {
            setTags([...tags, e.target.value]);
            e.target.value = "";
        }
    };

    const removeTags = (e) => {
        const removeIdx = e.target.parentNode.parentNode.id;
        if(removeIdx.length > 0) {
            setTags(tags.filter((tag, idx) => idx !== Number(removeIdx)));
        }
    };

    // const handleSubmit = () => {
    //     const formData = new FormData();

    //     files.forEach((file, idx) => {
    //         formData.append(`file${idx + 1}`, file);
    //     });
    //     formData.append("requestBody", new Blob([JSON.stringify({
    //         "memberId": Number(cookie.get("memberId")),
    //         "postingContent": value,
    //         "tagName": tags
    //     })], { type: "application/json"}));

    //     axios({
    //         method: "patch",
    //         url: "http://13.124.33.113:8080/posts/cookie.get("memberId")",
    //         data: {
    //        "postingId" : 1,
    //        "postingContent" : "게시글 수정 test",
    //        "tagName" : [ "스투키", "몬스테라" ]
    //         } 
    //         headers: { "Contest-Type": "multipart/form-data", Authorization: cookie.get("authorization") }
    
    //         }).then(res => {
    //             console.log(res);
    //         })
    //         .catch(e => {
    //             //에러 처리
    //         });
    // };

    return (
        <Wrapper>
            <CloseBtn handleEvent={handleEdit}/>
            <div className="preview">
                <Uploader images={images} handleImg={handleImg} />
                <ul>
                    {
                        curPost.postingMedias.map((media, idx) => {
                            return <li><img src={media.mediaUrl} alt="img" /></li>
                        })
                    }
                </ul>
            </div>
            <StyledTextarea value={value} onChange={handleValue} placeholder="수정할 거에용">{value}</StyledTextarea>
            <Tags tags={tags} addTags={addTags} removeTags={removeTags} />
            <div>
                <BlueBtn>수정하기</BlueBtn>
                <BlueBtn onClick={handleEdit}>취소</BlueBtn>
            </div>
        </Wrapper>
    )
}

export default EditPost;