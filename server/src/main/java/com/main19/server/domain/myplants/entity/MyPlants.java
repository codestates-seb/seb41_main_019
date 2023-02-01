package com.main19.server.domain.myplants.entity;

import com.main19.server.domain.myplants.gallery.entity.Gallery;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.main19.server.domain.member.entity.Member;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MyPlants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myPlantsId;

    @Column(nullable = false)
    private String plantName;

    @Column(nullable = false)
    private String plantType;

    @Column(nullable = false)
    private String plantBirthDay;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToMany(mappedBy = "myPlants" , cascade = CascadeType.REMOVE)
    private List<Gallery> galleryList = new ArrayList<>();

    public long getMemberId() {
        return member.getMemberId();
    }
}
