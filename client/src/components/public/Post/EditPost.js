import styled from "styled-components";
import Uploader from "./Uploader";
import Tags from "./Tags";
import CloseBtn from "../CloseBtn";
import { useRef, useState } from "react";
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
        margin: 0 0 -30px auto;
        
    }

    button:last-of-type{
        background-color: #D96848;
    }

    @media screen and (max-width: 1255px) {
        width: 700px;
    }

    @media screen and (max-width: 770px) {
        width: 360px;
        font-size: 12px;

        .btns {
            button {
                width: 50px;
                font-size: 12px;
                margin-right: 10px;
            }
        }

        input, textarea {
            font-size: 12px;
        }
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

// 기능 추가: 태그 줄 자동바꿈
const EditPost = ({ handleEdit, curPost, handleChange, change }) => {
    const [images, setImages] = useState(curPost.postingMedias);
    const [files, setFiles] = useState(curPost.postingMedias);
    const [value, setValue] = useState(curPost.postingContent);
    const [tags, setTags] = useState(curPost.tags);
    const [deleted, setDeleted] = useState([]);
    const fileInputs = useRef(null);
    const cookie = new Cookie();

    const handleImg = (e) => {
        const file = e.target.files[0];
        setFiles([...files, file]);
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = () => {
            setImages([...images, {mediaId: -1, mediaUrl: reader.result}]);
        }
    };

    const deleteImg = (e) => {
        const delIdx = e.target.id;
        if(files[delIdx].mediaId > 0) {
            setDeleted([...deleted, files[delIdx].mediaId]);
        }
        setFiles(files.filter((file, idx) => idx !== Number(delIdx)));
        setImages(images.filter((img, idx) => idx !== Number(delIdx)));
        if(images[delIdx].mediaId < 0) {
            fileInputs.current.childNodes[delIdx].remove();
        }   
    };

    const handleValue = (e) => {
        setValue(e.target.value);
    };

    const addTags = (e) => {
        if(e.key === "Enter" && e.target.value.length > 0 && tags.length < 5) {
            setTags([...tags, {tagName: e.target.value}]);
            e.target.value = "";
        }
    };

    const removeTags = (e) => {
        const removeIdx = e.target.parentNode.parentNode.id;
        if(removeIdx.length > 0) {
            setTags(tags.filter((tag, idx) => idx !== Number(removeIdx)));
        }
    };

    const handleSubmit = (e) => {
        // 삭제된 이미지를 제거한다.
        if(deleted.length > 0 && files.length > 0) {
            deleted.forEach((id) => {
                axios({
                    method: "delete",
                    url: `${process.env.REACT_APP_API}/posts/medias/${id}`,
                    headers: { Authorization: cookie.get("authorization") }
                }).then(res => {
                }).catch(e => {
                    console.log(e);
                })
            })
        }

        // 새로운 이미지를 추가한다.
        const newFiles = files.filter(file => file.name);
        if(newFiles.length > 0) {
            let formData = new FormData();
            newFiles.forEach((file, idx) => {
                formData.append(`file${idx + 1}`, file);
            })

            axios({
                method: "post",
                url: `${process.env.REACT_APP_API}/posts/${curPost.postingId}/medias`,
                data: formData,
                headers: { Authorization: cookie.get("authorization") }
            }).then(res => {
            }).catch(e => {
                console.log(e);
            })
        }

        // 게시글의 변경점을 추가한다.
        axios({
            method: "patch",
            url: `${process.env.REACT_APP_API}/posts/${curPost.postingId}`,
            data: JSON.stringify({
                postingContent: value,
                tagName: tags.map(tag => tag.tagName)
            }),
            headers: { Authorization: cookie.get("authorization"), "Content-Type": "application/json" }
        }).then(res => {
        }).catch(e => {
            console.log(e);
        })

        handleEdit(false);
        handleChange(!change);
    };

    return (
        <Wrapper>
            <CloseBtn handleEvent={handleEdit}/>
            <Uploader images={images} handleImg={handleImg} deleteImg={deleteImg} 
                fileInputs={fileInputs} edit/>
            <StyledTextarea value={value} onChange={handleValue}
                placeholder="당신의 식물을 소개해주세요.">
                    {value}
            </StyledTextarea>
            <Tags tags={tags} addTags={addTags} removeTags={removeTags} edit/>
            <div className="btns">
                <BlueBtn onClick={handleSubmit}>작성</BlueBtn>
                <BlueBtn onClick={handleEdit}>취소</BlueBtn>
            </div>
        </Wrapper>
    )
}

export default EditPost;