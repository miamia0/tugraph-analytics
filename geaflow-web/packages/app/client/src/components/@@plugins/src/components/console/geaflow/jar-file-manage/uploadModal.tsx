import { Modal, Form, Input, message, Upload } from "antd";
import { UploadOutlined } from "@ant-design/icons";
import React, { useEffect, useState } from "react";
import { createUpload } from "../services/file-manage";
import { InboxOutlined } from "@ant-design/icons";

const { Dragger } = Upload;
interface AddTemplateProps {
  isAddMd?: any;
  setTemplateList?: any;
  temeplateList?: any;
  id: string;
}

export const AddTemplateModal: React.FC<AddTemplateProps> = ({
  isAddMd,
  setTemplateList,
  temeplateList,
  id,
}) => {
  const [form] = Form.useForm();
  const [state, setState] = useState({
    loading: false,
    fileList: [],
  });
  const handleCancel = () => {
    setTemplateList({ ...temeplateList, isAddMd: false });
    form.resetFields();
  };
  const handleOk = async () => {
    const values = await form.validateFields();

    setState({
      ...state,
      loading: true,
    });
    const { file } = values;

    const formData = new FormData();

    file.forEach((item) => {
      formData.append("file", item.originFileObj);
    });
    const resp = await createUpload(id, formData);

    setState({
      ...state,
      loading: false,
    });
    if (resp?.success) {
      message.success("上传成功");
      setTemplateList({ ...temeplateList, isAddMd: false });
      form.resetFields();
    } else {
      message.error(resp?.message);
    }
  };

  const normFile = (e: any) => {
    console.log(e);
    if (Array.isArray(e)) {
      return e;
    }
    return e?.fileList.slice(-1);
  };

  return (
    <Modal
      title="上传文件"
      visible={isAddMd}
      onOk={handleOk}
      onCancel={handleCancel}
      confirmLoading={state.loading}
      width={700}
      okText="确认"
      cancelText="取消"
    >
      <Form form={form}>
        <Form.Item label="Jar文件">
          <Form.Item
            name="file"
            valuePropName="fileList"
            getValueFromEvent={normFile}
            noStyle
          >
            <Upload.Dragger>
              <p className="ant-upload-drag-icon">
                <InboxOutlined />
              </p>
              <p className="ant-upload-text">拖拽或点击选择文件</p>
              <p className="ant-upload-hint">只支持 jar 文件。</p>
            </Upload.Dragger>
          </Form.Item>
        </Form.Item>
      </Form>
    </Modal>
  );
};
