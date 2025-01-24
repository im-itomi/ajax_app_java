package in.tech_camp.ajax_app_java.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.ajax_app_java.entity.PostEntity;
import in.tech_camp.ajax_app_java.form.PostForm;
import in.tech_camp.ajax_app_java.repository.PostRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PostController {

  private final PostRepository postRepository;

  @GetMapping("/")
  public String showList(Model model) {
    var postList = postRepository.findAll();
    model.addAttribute("postList", postList);
    model.addAttribute("postForm", new PostForm());
    return "posts/index";
  }

  // フォームをトップページに表示するようにしたため、不要により削除
  // @GetMapping("/postForm")
  // public String showPostForm(@ModelAttribute("postForm") PostForm form){
  //     return "posts/postForm";
  // }

  // 同期通信でHTMLファイルを返却する場合の処理
  // @PostMapping("/posts")
  // public String savePost(@ModelAttribute("postForm") PostForm form){
  //   PostEntity post = new PostEntity();
  //   post.setContent(form.getContent());
  //   postRepository.insert(post);
  //   return "redirect:/";
  // }

  // 非同期通信
  @PostMapping("/posts")
  public ResponseEntity<PostEntity> savePost(@ModelAttribute("postForm") PostForm form){
    PostEntity post = new PostEntity();
    post.setContent(form.getContent());
    postRepository.insert(post);
    // 作成したデータをEntityに保存
    PostEntity resultPost = postRepository.findById(post.getId());
    return ResponseEntity.ok(resultPost);
  }
  
}
