#ifndef __CAE_INTF_H__
#define __CAE_INTF_H__

typedef void * CAE_HANDLE; 

//angle �Ƕ�   
//channel  ���Ⲩ�����
//power ��������
//CMScore ���ѷ�ֵ
//beam ��˷��� 
//userData �û��ص�����
typedef void (*cae_ivw_fn)(short angle, short channel, float power, short CMScore, short beam, char *param1, void *param2, void *userData);


//audioData ʶ����Ƶ��ַ
//audioLen  ʶ����Ƶ�ֽ���
//userData �û��ص�����
typedef void (*cae_audio_fn)(const void *audioData, unsigned int audioLen, int param1, const void *param2, void *userData);

//audioData ������Ƶ��ַ
//audioLen  ������Ƶ�ֽ���
//userData �û��ص�����
typedef void (*cae_ivw_audio_fn)(const void *audioData, unsigned int audioLen, int param1, const void *param2, void *userData);

#ifdef __cplusplus
extern "C" {
#endif /* __cplusplus */


/* ��ʼ��ʵ��(ʵ����ַ����Դ��ַ��������Ϣ�ص���������Ƶ�ص���ʶ����Ƶ�ص���Ԥ���������û��ص�����) */
int CAENew(CAE_HANDLE *cae, const char* resPath, cae_ivw_fn ivwCb, cae_ivw_audio_fn ivwAudioCb, cae_audio_fn audioCb, const char *param, void *userData);
typedef int (* Proc_CAENew)(CAE_HANDLE *cae, const char* resPath, cae_ivw_fn ivwCb, cae_ivw_audio_fn ivwAudioCb, cae_audio_fn audioCb, const char *param, void *userData);

/* ���¼�����Դ(ʵ����ַ����Դ·��) */
int CAEReloadResource(CAE_HANDLE cae, const char* resPath);
typedef int (* Proc_CAEReloadResource)(CAE_HANDLE cae, const char* resPath);

/* д����Ƶ����(ʵ����ַ��¼�����ݵ�ַ��¼�����ݳ���) */
int CAEAudioWrite(CAE_HANDLE cae, const void *audioData, unsigned int audioLen);
typedef int (* Proc_CAEAudioWrite)(CAE_HANDLE cae, const void *audioData, unsigned int audioLen);

/* ������˷���(����ģʽ�ڲ��Ѿ����ñ���ⲿ�����ٴε��á��ֶ�ģʽ��Ҫ����������˷���) */
int CAESetRealBeam(CAE_HANDLE cae, int beam);
typedef int (* Proc_CAESetRealBeam)(CAE_HANDLE cae, int beam);

/* ��ȡ�汾�� */
char* CAEGetVersion();
typedef char (* Proc_CAEGetVersion)();

/* ����ʵ��(ʵ����ַ) */
int CAEDestroy(CAE_HANDLE cae);
typedef int (* Proc_CAEDestroy)(CAE_HANDLE cae);

/* ������־����(��־���� 0 ���ԡ�1 ��Ϣ��2����) */
int CAESetShowLog(int show_log);
typedef int (* Proc_CAESetShowLog)(int show_log);

/* �����Ȩ(�豸��Ȩ���)*/
int CAEAuth(char *sn);
typedef int (* Proc_CAEAuth)(char *sn);

#ifdef __cplusplus
}
#endif /* __cplusplus */

#endif /* __CAE_INTF_H__ */