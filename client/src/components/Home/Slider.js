import styled from "styled-components";
import { useState } from "react";
import { IoIosArrowDropleft, IoMdEgg } from "react-icons/io";
import { IoIosArrowDropright } from "react-icons/io";

const Wrapper = styled.div`
    width: 100%;
    height: ${({type}) => type === "post" ? "500px" : "100%"};

    ul {
        margin: 0px;
        padding: 0px;
        list-style: none;
        width: 100%;
        height: 100%;

        li {
            width: 100%;
            height: 100%;
        }

        li div:first-child {
            height: ${({type}) => type === "post" ? "500px" : "100%"};
            background-color: black;
            width: 100%;
        }

        li div img {
            object-fit: contain;
            width: 100%;
            height: 100%;
        }

        li div video {
            object-fit: contain;
            width: 100%;
            height: 100%;
        }

        li div:nth-of-type(2) {
            display: flex;
            justify-content: space-between;
            position: relative;
            top: -50%;
            width: 100%;

            .hidden {
                visibility: hidden;
            }

            svg {
                fill: #ADADAD;
                margin: 0px 10px 0px 10px;
                border-radius: 30px;
            }
        }
        
        .none {
            display: none;
        }
    }

    @media screen and (max-width: 770px) {
        height: ${({type}) => type === "post" ? "360px" : "100%"};
        ul li div:first-child {
            height: ${({type}) => type === "post" ? "360px" : "100%"}; 
        }

        ul li div img {
            height: ${({type}) => type === "post" ? "360px" : "100%"};
        }
    }
`;

const Slider = ({ imgs, type = "view" }) => {
    const [cur, setCur] = useState(0);
    
    return (
        <Wrapper type={type} >
            <ul>
                { imgs.map((img, idx) => {
                        return (
                            <li key={idx} className={cur === idx ? null : "none"}>
                                <div>
                                    {
                                        img.format === "video" && <video src={img.mediaUrl} poster={img.thumbnailUrl} controls/>
                                    }
                                    {
                                        img.format === "image" && <img src={img.mediaUrl} alt="img" />
                                    }
                                </div>
                                { imgs.length > 1 ?
                                    <div className="arrow">
                                        <IoIosArrowDropleft onClick={() => setCur(cur - 1)} className={`pre ${cur === 0 ? "hidden" : null}`} />
                                        <IoIosArrowDropright onClick={() => setCur(cur + 1)} className={`next ${cur === imgs.length - 1 ? "hidden" : null}`} />
                                    </div> 
                                    : null
                                }
                            </li>
                        );
                    })
                }
            </ul>
        </Wrapper>
    )
};

export default Slider;