# Opensource Document Management Project

Which forked from https://git.oschina.net/ppm/ppm-open.git. Thanks very much for that project.


# 开源文档管理系统项目

从 https://git.oschina.net/ppm/ppm-open.git 克隆而来，感谢原项目的努力和无私贡献！因为这个项目应该有四年多没有更新了，所以我克隆了一个过来继续修复BUG、定制个功能什么的。

以下是原版的介绍。

--------------------------------------------

## PPM开源项目

PPM项目主页：http://www.ppm123.cn

PPM管理系统完全免费、开源，致力于研发国内最精致，最实用的个人、企业信息化管理系统，提高个人与企业的效率！

## 源码使用

PPM开发环境从MyEclipse迁移至Intellij Idea。

1. 打开Intellij IDEA -- VCS菜单 -- Checkout Form Version Control -- Git  -- 复制Git地址 -- 检出项目。

2. 执行/web/WEB-INF/sql/目录下的SQL脚本，配置jdbc.properties。

3. 配置open-platform build.xml。执行Ant命令。open-platform为平台模块，不独立运行，而是通过build-***.xml Ant至目标项目。

4. 配置Intellij IDEA tomcat，部署运行。

5. 初始登录用户为：admin，密码为：1

此项目包含了PPM技术平台和所有的PPM开源项目：

## open-platform 平台系统

1. Security权限模型

2. Entity实体自动化

3. Attach附件处理

4. Platform工具集

5. UI前端插件

6. Tags自定义标签

## open-doc 文档管理系统

PPM Doc 文档管理系统是国内第一款开源的企业文档管理系统，旨在帮助企业摆脱VSS等粗糙的文档管理软件，方便、快捷管理并协同企业文档！

1. 文档仓库 -- 简单清爽的界面风格，Windows文件目录式操作习惯

2. 版本控制 -- 文档检入检出，控制文档版本，部门内文档协同

3. 版本比较 -- 对比文档的不同版本，直观查看不同版本间差异

4. 在线浏览 -- 在线浏览文本、图片、Office文件、PDF等文档

5. 全文检索 -- 全文检索文本型文件及Office文件内部内容

## android-contact 企业通讯录

PPM Contact 企讯通旨在构建现代化的企业社交，通讯体系，提高企业内部沟通效率，互联企业人员及企业移动社交！

1. 移动组织架构，在手机上查看整个企业的组织架构
2. 云通讯录，PPM提供了基于WEB的维护通讯录信息的系统，云端维护实时更新到移动端
3. 移动通讯录，下载PPM企讯通APP，即可获得整个企业人员的通讯信息，直接呼叫或发送信息
