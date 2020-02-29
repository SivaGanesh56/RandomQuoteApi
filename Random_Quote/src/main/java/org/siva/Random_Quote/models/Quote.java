package org.siva.Random_Quote.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quote {
    private String content;
    private String author;

    @Override
    public String toString() {
        return "Quote{" +
                "content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public String print(){
        return this.getContent()+"\n\n --->By "+ this.getAuthor();
    }

}