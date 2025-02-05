# 通用的BasicUILib，可以用在Desktop， Android,内置BasicMultiplatformLib
# 技术栈
- compose ui图标使用br.com.devsrsouza.compose.icons:tabler-icons

# 使用
## 本库依赖BasicMultiplatformLib,初始化时必须先初始化BasicMultiplatformLib才能初始化本库
## 初始化BasicUILib, 见BasicUILib文档
## 初始化平台UIInitCenter
- AndroidUIInitCenter
- JVMInitUICenter
## 渲染UI界面时使用作为BasicMUIWrapper根节点，可以提供主题统一管理, Toast
# 功能
## 主题管理, 使用ThemeRepository
## Toast
## UI捕获异常