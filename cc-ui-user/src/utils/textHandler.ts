export const truncateText = (text:string, maxLength:number) => {
    if (text.length > maxLength) {
        return text.slice(0, maxLength) + '...'; // 如果文本长度超过最大长度，截断文本并添加省略号
    }
    return text; // 否则，返回原始文本
}