<template>
    <div class="edit_container">
        <!--  新增时输入 -->
        <quill-editor 
            :value="value"
            ref="edit" 
            :options="editorOption()" 
            @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
            @input.self.stop="handleInput"
            @change="onEditorChange($event)">
        </quill-editor>
    </div>
</template>
<script>
import { quillEditor } from "vue-quill-editor"; //调用编辑器
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';
export default {
    components: {
        quillEditor
    },
    props: {
      value: String,
      toolbarOptions: Array
    },
    // watch: {
    //   value (val, oldVal) {
    //     if (!this.focus) {
    //       this.$refs.edit.quill.enable(false);
    //       this.$nextTick(() => {
    //         this.$refs.edit.quill.blur();
    //         this.$refs.edit.quill.enable(true);
    //       });
    //     }
    //     if (val && !oldVal) {
    //       this.value = val;
    //     }
    //   }
    // },
    data() {
      return {
        focus: false
      }
    },
    methods: {
        onEditorReady(editor) { // 准备编辑器
 
        },
        onEditorBlur(){
          debugger
          this.focus = false;
        }, // 失去焦点事件
        onEditorFocus(){
          debugger
          this.focus = true;
        }, // 获得焦点事件
        onEditorChange(){}, // 内容改变事件
        handleInput (content) {
          debugger
          this.value = content;
        },
        editorOption () {
          const config = {
            modules: {
              toolbar: this.toolbarOptions
            }
          };
          return config;
        },
    }
}
</script>
